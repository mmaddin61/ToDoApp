package com.group1.todoapp

import org.w3c.dom.Document
import org.w3c.dom.Element

data class TaskData(var title : String, var description : String, var finished : Boolean) {
    companion object {
        fun Export(doc : Document, root : Element, todoData : TaskData) {
            val taskElement = doc.createElement("Task")
            taskElement.setAttribute("title", todoData.title)
            taskElement.setAttribute("desc", todoData.description)
            taskElement.setAttribute("status", todoData.finished.toString())

            root.appendChild(taskElement)
        }

        fun Import(taskElement : Element) : TaskData {
            val taskTitle = taskElement.getAttribute("title")
            val taskDesc = taskElement.getAttribute("desc")
            val taskStatus = taskElement.getAttribute("status").toBoolean()

            return TaskData(taskTitle, taskDesc, taskStatus)
        }
    }
}
