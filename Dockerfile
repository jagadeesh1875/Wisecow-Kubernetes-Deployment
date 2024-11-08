# Dockerfile 
# Start with a base Python image
FROM python:3.9-slim

# Set the working directory
WORKDIR /app

# Copy application requirements file and install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the entire application code into the container
COPY . .

# Expose the port that the application will run on
EXPOSE 8080

# Start the application
CMD ["python", "app.py"]

