package com.group1.todoapp

import android.os.Debug
import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class UserDataFactory {
    companion object {
        private const val USER_DATA_FILE : String = "userData.xml"

        private var currentUserId = 0 //global index to keep track of unique Todo entries

        private val userTodoData = mutableMapOf<Int, TodoData>()

        fun AddTodo(todo : TodoData) {
            todo.id = currentUserId++
            userTodoData[todo.id] = todo
        }

        fun RemoveTodo(id : Int) {
            userTodoData.remove(id)
        }

        fun UpdateTodo(id : Int, data : TodoData) {
            if (!userTodoData.containsKey(id)) {
                throw Exception("User ToDo data not found")
            }

            userTodoData[id] = data
        }

        fun GetTodo(id : Int) : TodoData {
            if (!userTodoData.containsKey(id)) {
                throw Exception("User ToDo data not found")
            }

            return userTodoData[id]!!
        }

        fun GetTodoList() : List< TodoData > {
            return userTodoData.values.toList()
        }

        fun LoadUserData(path : String) {
            Log.d("USER_FACTORY", "Loading user data...")

            val file = File(path, USER_DATA_FILE)

            Log.d("USER_FACTORY", file.readText())

            if (!file.exists()) {
                file.createNewFile()
                return;
            }

            userTodoData.clear()

            val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val document = docBuilder.parse(file)

            document.documentElement.normalize()

            val userId = document.getElementsByTagName("CurrentUserId").item(0)

            currentUserId = userId?.nodeValue?.toInt() ?: 0

            val nodes = document.getElementsByTagName("Todo")

            for (n in 0 until nodes.length) {
                val node = nodes.item(n)

                if (node.nodeType != Document.ELEMENT_NODE) {
                    continue
                }

                val element = node as Element

                if (element.nodeName != "Todo") {
                    continue;
                }

                val todoData = TodoData.Import(element)
                userTodoData[todoData.id] = todoData
            }
        }

        fun SaveUserData(path : String) {
            Log.d("USER_FACTORY", "Saving user data...")

            val file = File(path, USER_DATA_FILE)

            if (!file.exists()) {
                file.createNewFile()
            }

            val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val document = docBuilder.newDocument()

            val root = document.createElement("UserData")
            val userElement = document.createElement("CurrentUserId")

            userElement.appendChild(document.createTextNode(currentUserId.toString()))
            root.appendChild(userElement)

            userTodoData.forEach() {
                TodoData.Export(document, root, it.value)
            }

            document.appendChild(root)

            Log.d("USER_FACTORY", root.textContent)

            val transformer = TransformerFactory.newInstance().newTransformer()
            val source = DOMSource(document)
            val result = StreamResult(file)

            transformer.transform(source, result)

            Log.d("USER_FACTORY", file.readText())
        }
    }
}