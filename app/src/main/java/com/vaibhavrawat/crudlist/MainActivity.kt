package com.vaibhavrawat.crudlist

import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaibhavrawat.crudlist.databinding.ActivityMainBinding
import com.vaibhavrawat.crudlist.databinding.CustomDialogLayoutBinding
import com.vaibhavrawat.recyclerlistproject.RecyclerViewAdapter


class MainActivity : AppCompatActivity(), ListClickInterface {
    lateinit var binding: ActivityMainBinding
    lateinit var adapterRecycle: RecyclerViewAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var notesDB: NotesDB
    lateinit var notesInterface: NotesInterface
    var arrayList = ArrayList<NotesEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notesDB = NotesDB.getNotesDatabase(this)
        adapterRecycle = RecyclerViewAdapter(arrayList, this)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.RecyclerList.layoutManager = layoutManager
        binding.RecyclerList.adapter = adapterRecycle
        getNotes()
        binding.fab.setOnClickListener {
            val dialogBinding = CustomDialogLayoutBinding.inflate(layoutInflater)
            val dialog = Dialog(this)
            dialog.setContentView(dialogBinding.root)
            dialog.setCancelable(false)
            dialogBinding.btnAdd.setOnClickListener {
                class Insert : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg params: Void?): Void? {
                        notesDB.notesDao().insertNoteData(
                            NotesEntity(
                                title = dialogBinding.etName.text.toString(),
                                description = dialogBinding.etRoll.text.toString()
                            )
                        )
                        return null
                    }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        getNotes()
                    }
                }
                Insert().execute()
                adapterRecycle.notifyDataSetChanged()
                dialog.dismiss()
            }
            dialogBinding.btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    fun getNotes() {
        arrayList.clear()
        class Retrive : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                arrayList.addAll(notesDB.notesDao().getNotes())
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                adapterRecycle.notifyDataSetChanged()
            }
        }
        Retrive().execute()
    }

    override fun onUpdateClick(position: Int) {
        val dialogBinding = CustomDialogLayoutBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)

        // Get the existing note at the specified position
        val existingNote = arrayList[position]
        dialogBinding.etName.setText(existingNote.title)
        dialogBinding.etRoll.setText(existingNote.description)

        dialogBinding.btnAdd.setOnClickListener {
            class Update : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg params: Void?): Void? {
                    // Update the existing note with the new title and description
                    existingNote.title = dialogBinding.etName.text.toString()
                    existingNote.description = dialogBinding.etRoll.text.toString()
                    notesDB.notesDao().updateNotes(existingNote)
                    return null
                }

                override fun onPostExecute(result: Void?) {
                    super.onPostExecute(result)
                    updateNotes()
                }
            }
            Update().execute()
            dialog.dismiss()
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun updateNotes() {
        class UpdateNotesTask : AsyncTask<Void, Void, List<NotesEntity>>() {
            override fun doInBackground(vararg params: Void?): List<NotesEntity> {
                return notesDB.notesDao().getNotes()
            }

            override fun onPostExecute(result: List<NotesEntity>?) {
                super.onPostExecute(result)
                if (result != null) {
                    arrayList.clear()
                    arrayList.addAll(result)
                    adapterRecycle.notifyDataSetChanged()
                }
            }
        }
        UpdateNotesTask().execute()
    }

    override fun onDelete(position: Int) {
        class Delete : AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                notesDB.notesDao().deleteNotes(arrayList[position])
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                getNotes()
            }

        }
        Delete().execute()
    }
}