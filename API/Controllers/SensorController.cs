using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using LeakSentryAPI.DB;
using LeakSentryAPI.Models;
using LeakSentryAPI.Firebase;
using LeakSentryAPI.Utils;
using Microsoft.AspNetCore.Cors;

namespace LeakSentryAPI.Controllers
{
    [EnableCors("AllowAllPolicy")] 
    public class SensorController : Controller
    {
        // adds a new sensor
        [HttpPost]
        public JsonResult addSensor(int id)
        {
            if(id <= 0)
                return Json(new ApiError("Invalid request parameters."));

            try
            {
                using(SensorContext db = new SensorContext())
                {
                    // check if id already exists
                    if(db.Sensors.Any(sensor => sensor.ID == id))
                        return Json(new ApiError("Sensor already exists"));

                    db.Sensors.Add(new Sensor(id));
                    db.SaveChanges();
                    return Json(new { succeeded = true });
                }
            }
            catch(Exception e)
            {
                return Json(new ApiError("An error occured."));
            }
        }

        // subscribes a device to the sensor
        [HttpPost]
        public JsonResult SubscribeTo(int sensorId, string deviceId)
        {
            if(sensorId <= 0 || !Validator.Present(deviceId))
                return Json(new ApiError("Invalid request parameters."));

            try
            {
                using(SensorContext db = new SensorContext())
                {
                    Sensor sensor = db.Sensors.Single(s => s.ID == sensorId);

                    if(sensor.GetNotificationIds().Any(dID => dID == deviceId))
                        return Json(new ApiError("Device is already subscribed to sensor."));
                    
                    sensor.GetNotificationIds().Add(deviceId);
                    db.SaveChanges();
                    return Json(new { succeeded = true });
                }
            }
            catch(Exception e)
            {
                return Json(new ApiError("An error occured."));
            }
        }

        // unsubscribes a device from the sensor
        [HttpPost]
        public JsonResult UnSubscribeFrom(int sensorId, string deviceId)
        {
            if(sensorId <= 0 || !Validator.Present(deviceId))
                return Json(new ApiError("Invalid request parameters."));

            try
            {
                using(SensorContext db = new SensorContext())
                {
                    Sensor sensor = db.Sensors.Single(s => s.ID == sensorId);

                    if(!sensor.GetNotificationIds().Any(dID => dID == deviceId))
                        return Json(new ApiError("Device isn't subscribed to sensor."));
                    
                    sensor.GetNotificationIds().Remove(deviceId);
                    db.SaveChanges();
                    return Json(new { succeeded = true });
                }
            }
            catch(Exception e)
            {
                return Json(new ApiError("An error occured."));
            }
        }

        // sends a message to devices subscribed to sensor
        [HttpPost]
        public JsonResult Notify(int sensorId, Dictionary<string, string> data)
        {
            if(sensorId <= 0 || data == null)
                return Json(new ApiError("Invalid request parameters."));

            try
            {
                using(SensorContext db = new SensorContext())
                {
                    Sensor sensor = db.Sensors.Single(s => s.ID == sensorId);
                    FcmMessage msg = new FcmMessage(data, sensor.GetNotificationIds().ToArray());
                    var response = FirebaseClient.instance.Notify(msg);
                    
                    if(response.IsSuccessStatusCode)
                        return Json(new { succeeded = true });
                    else
                        return Json(new ApiError("Message wasn't sent."));
                }
            }
            catch(Exception e)
            {
                return Json(new ApiError("An error occured."));
            }
        }
    }
}
