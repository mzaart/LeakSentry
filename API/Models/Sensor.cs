using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using LeakSentryAPI.Utils;

namespace LeakSentryAPI.Models
{
      public class Sensor
      {
            [Key]
            public int ID { get; set; }

            [Required]
            public string jsonIds
            {
                  get
                  {
                        if (notificationIds != null)
                              notificationIds = new List<string>();

                        return JsonConvert.SerializeObject(notificationIds);
                  }

                  set
                  {
                        if (!Utils.Validator.Present(value))
                              notificationIds = JsonConvert.DeserializeObject<List<string>>(value);
                        else
                              notificationIds = new List<string>();
                  }
            }

            private List<string> notificationIds;

            public Sensor(int id)
            {
                  this.ID = id;
                  notificationIds = new List<string>();
            }

            // empty constructor for entity framewrok
            private Sensor()
            {
                  if (string.IsNullOrEmpty(jsonIds))
                        notificationIds = new List<string>();
                  else
                        notificationIds = JsonConvert.DeserializeObject<List<string>>(jsonIds);
            }

            public List<string> GetNotificationIds() 
            {
                  return notificationIds;
            }
      }
}