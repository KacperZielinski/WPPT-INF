package com.example.todo2

import java.util.Date

class Task(var date: Date,
           var content: String,
           var taskType: TaskType,
           var priority: Int)