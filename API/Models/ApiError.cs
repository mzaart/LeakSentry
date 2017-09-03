using System;

namespace LeakSentryAPI.Models
{
      public class ApiError
      {
            public string error { get; set; }

            public ApiError(string error)
            {
                  this.error = error;
            }
      }
}