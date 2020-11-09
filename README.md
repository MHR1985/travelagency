## Travelagency

This application can create offers and send to queue's on RabbitMQ, while listening for accepted offers.
It also persists customers, offers and bookings in a database.

### How to install and run
run these commands from console (you must have docker desktop running):
```
docker pull rabbitmq:3-management
```
```
docker run -d -p 15672:15672 -p 5672:5672 --name rabbit-test-for-medium rabbitmq:3-management
```

Then run MainTravelAgency to start application