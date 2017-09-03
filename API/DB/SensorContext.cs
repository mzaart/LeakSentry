using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using LeakSentryAPI.Models;

namespace LeakSentryAPI.DB
{
    public class SensorContext : DbContext
    {
        public DbSet<Sensor> Sensors { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlite("Filename=./sensors.db");
        }
    }
}