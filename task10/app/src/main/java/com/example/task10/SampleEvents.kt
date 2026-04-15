package com.example.task10

object SampleEvents {
    fun getEvents(): ArrayList<Event> {
        return arrayListOf(
            Event(1,"AI Summit","2026-04-10","10:00 AM","Auditorium","Tech","A major AI conference with talks and demos.",500.0,48,30,android.R.drawable.ic_menu_gallery),
            Event(2,"Coding Contest","2026-04-15","02:00 PM","Lab 5","Academic","Competitive programming event for students.",200.0,48,26,android.R.drawable.ic_menu_gallery),
            Event(3,"Football Final","2026-04-18","04:00 PM","Main Field","Sports","Inter-department championship final.",150.0,48,20,android.R.drawable.ic_menu_gallery),
            Event(4,"Cultural Night","2026-04-20","06:00 PM","Open Stage","Cultural","Music, dance and drama performances.",300.0,48,18,android.R.drawable.ic_menu_gallery),
            Event(5,"Research Fair","2026-04-25","11:00 AM","Hall B","Academic","Student research project showcase.",100.0,48,32,android.R.drawable.ic_menu_gallery),
            Event(6,"Startup Meetup","2026-04-28","03:00 PM","Seminar Room","Tech","Founders and students networking session.",250.0,48,28,android.R.drawable.ic_menu_gallery),
            Event(7,"Charity Run","2026-05-01","07:00 AM","Campus Gate","Social","Fundraising run for community support.",120.0,48,16,android.R.drawable.ic_menu_gallery),
            Event(8,"Debate Championship","2026-05-05","01:00 PM","Room 301","Social","Public speaking and debate finals.",180.0,48,22,android.R.drawable.ic_menu_gallery)
        )
    }
}