import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class SystemHealthMonitor {

    private static final double CPU_THRESHOLD = 80.0;
    private static final double MEMORY_THRESHOLD = 80.0;
    private static final double DISK_THRESHOLD = 80.0;

    public static void main(String[] args) {
        checkCpuUsage();
        checkMemoryUsage();
        checkDiskUsage();
    }

    private static void checkCpuUsage() {
        try {
            Process process = Runtime.getRuntime().exec("top -bn1 | grep 'Cpu(s)'");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                String[] cpuInfo = line.split(",");
                String idleStr = cpuInfo[3].replaceAll("[^0-9.]", "").trim();
                double idle = Double.parseDouble(idleStr);
                double cpuUsage = 100.0 - idle;

                if (cpuUsage > CPU_THRESHOLD) {
                    System.out.println("Alert: CPU usage is above threshold at " + cpuUsage + "%");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkMemoryUsage() {
        try {
            Process process = Runtime.getRuntime().exec("free | grep Mem");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                String[] memoryInfo = line.split("\\s+");
                double totalMemory = Double.parseDouble(memoryInfo[1]);
                double usedMemory = Double.parseDouble(memoryInfo[2]);
                double memoryUsage = (usedMemory / totalMemory) * 100.0;

                if (memoryUsage > MEMORY_THRESHOLD) {
                    System.out.println("Alert: Memory usage is above threshold at " + memoryUsage + "%");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkDiskUsage() {
        try {
            Process process = Runtime.getRuntime().exec("df / | grep /");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                String[] diskInfo = line.split("\\s+");
                int diskUsage = Integer.parseInt(diskInfo[4].replace("%", ""));

                if (diskUsage > DISK_THRESHOLD) {
                    System.out.println("Alert: Disk usage is above threshold at " + diskUsage + "%");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
