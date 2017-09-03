using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;

namespace LeakSentryAPI.Firebase
{

    public class FcmMessage
    {
        // the ids of the devices to send the notification to
        private string[] registration_ids { get; set; }

        // the data to send with the message
        private Dictionary<string, string> data { set; get; }

        // the notification to send
        private Notification notification { get; set; }

        // determines whether the message is a notification
        private bool isNotification { get { return notification != null; } }

        // determines whether the message contains data
        private bool containsData { get { return data != null; } }

        // constructor for notification message with data 
        public FcmMessage(Notification body, Dictionary<string, string> data, string[] ids)
        {
            notification = body;
            this.data = data;
            registration_ids = ids;
        }

        // constructor for notification message without data 
        public FcmMessage(Notification body, string[] ids)
        {
            notification = body;
            registration_ids = ids;
        }

        // constructor for a non-notification message
        public FcmMessage(Dictionary<string, string> data, string[] ids)
        {
            this.data = data;
            registration_ids = ids;
        }
  
        // returns a json representation of the message to be sent to the FCM server
        public override string ToString()
        {
            Object message;

            if (!isNotification)
            {
                message = new
                {
                    data = this.data,
                    registration_ids = this.registration_ids
                };
            } else
            {
                if(containsData)
                {
                    message = new
                    {
                        notification = this.notification,

                        data = this.data,

                        registration_ids = this.registration_ids
                    };
                }
                else
                {
                    message = new
                    {
                        notification = this.notification,
                        registration_ids = this.registration_ids
                    };
                }
            }

            return JsonConvert.SerializeObject(message);
        }

        public class Notification
        {
            // title of the notification
            public string title;

            // notification text
            public string text;

            // the action to be performed when the notification is clicked
            public string click_action;

            public string sound;

            public Notification(string title, string text, string clickAction, string sound)
            {
                this.title = title;
                this.text = text;
                click_action = clickAction;
                this.sound = sound;
            }
        }
    }
}