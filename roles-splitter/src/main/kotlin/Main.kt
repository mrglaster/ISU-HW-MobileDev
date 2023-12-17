import java.io.FileInputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class DialogueRow(var dialogueRow:Int, var dialogueText:String)
val rolesQueue:ArrayList<String> = ArrayList<String>()

fun splitRoles(filePath:String) : HashMap<String, ArrayList<DialogueRow>>{
    val sc = Scanner(FileInputStream(filePath))
    var wasRoleSection:Boolean = false
    var wasTextSection:Boolean = false
    var currentTextRowNumber:Int = 1

    val dialogueRows: HashMap<String, ArrayList<DialogueRow>> = HashMap<String, ArrayList<DialogueRow>>()

    while (sc.hasNextLine()){
        val currentRow:String = sc.nextLine()
        if (currentRow == "roles:"){
            wasRoleSection = true;
            continue
        }

        if (currentRow == "textLines:"){
            wasTextSection = true;
            continue
        }

        if (wasRoleSection && !wasTextSection){
            dialogueRows[currentRow] = ArrayList<DialogueRow>()
            rolesQueue.add(currentRow)
        } else if (wasTextSection && wasTextSection){
            val parts = currentRow.split(":", limit = 2)
            dialogueRows[parts[0]]?.add(DialogueRow(currentTextRowNumber, parts[1]))
            currentTextRowNumber +=1
        }
    }
    return dialogueRows
}


fun printDialogues(dialogues:HashMap<String, ArrayList<DialogueRow>>){
    for (i in rolesQueue){
        println(i)
        for (j in dialogues[i]!!){
            println("${j.dialogueRow}) ${j.dialogueText}")
        }
        println("")
    }
}

fun main() {
   val dialogues:HashMap<String, ArrayList<DialogueRow>> = splitRoles("data.txt")
    printDialogues(dialogues)
}