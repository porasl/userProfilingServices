Usage
Train the Model:
Endpoint: POST /api/profiling/train
Body:

[
  [1.2, 2.3, 3.4, 4.5, 5.6, 0],
  [2.1, 3.2, 4.3, 5.4, 6.5, 1]
]

Each array contains features followed by the label (last value).
Predict User Profile:
Endpoint: POST /api/profiling/predict
Body:
{
  "features": [1.5, 2.6, 3.7, 4.8, 5.9]
}
Response:
{
  "profile": "Intermediate",
  "confidence": 0.95 
}


Run the Application
Start the Spring Boot application.
Use tools like Postman to test the APIs.
Save and persist the model using model.save() for production use.
This project provides a scalable, web-based user profiling service with training and prediction capabilities.