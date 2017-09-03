using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace LeakSentryAPI.Firebase
{
    public class FirebaseClient : HttpClient
    {
        // constants
        private static readonly string FIREBASE_URL = "https://fcm.googleapis.com";
        public static readonly string AUTHORIZATION_KEY = "AAAAG78OP-8:APA91bHMlXHSQZg4rHyzq5bA8Z1jcQ6uRKmb7s5ixbbfaqrH4Db5VArLLpLDxLvzUB9n8FAkArbA6RuT9PIRksUN7z4T_uv2JJD1yPMMcil9Z2zOzTZ60CZgu5HUb_roPx4vUhKinrfh";

        private static FirebaseClient client = new FirebaseClient();

        private FirebaseClient()
        {
            BaseAddress = new Uri(FIREBASE_URL);
            DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("key", "=" + AUTHORIZATION_KEY);
        }

        public static FirebaseClient instance { get { return client; } }

        public HttpResponseMessage Notify(FcmMessage n)
        {
            StringContent jsonContent = new StringContent(n.ToString(), Encoding.UTF8, "application/json");

            return PostAsync("fcm/send", jsonContent).Result;
        }
    }
}