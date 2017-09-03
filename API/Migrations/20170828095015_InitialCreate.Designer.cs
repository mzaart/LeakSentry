using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using LeakSentryAPI.DB;

namespace LeakSentryAPI.Migrations
{
    [DbContext(typeof(SensorContext))]
    [Migration("20170828095015_InitialCreate")]
    partial class InitialCreate
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
            modelBuilder
                .HasAnnotation("ProductVersion", "1.1.2");

            modelBuilder.Entity("LeakSentryAPI.Models.Sensor", b =>
                {
                    b.Property<int>("ID")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("jsonIds")
                        .IsRequired();

                    b.HasKey("ID");

                    b.ToTable("Sensors");
                });
        }
    }
}
