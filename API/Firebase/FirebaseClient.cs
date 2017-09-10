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
        public static readonly string AUTHORIZATION_KEY = "insert your auth key here";

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
