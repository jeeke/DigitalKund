package com.p3cs.digitalkund.data.repos.baseRepo

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.p3cs.digitalkund.data.repos.mapper.baseMapper.BaseValueEventListener
import com.p3cs.digitalkund.data.repos.mapper.baseMapper.ResponseMapper

abstract class BaseRepository<FROM , TO> {

    lateinit var databaseReference: DatabaseReference
    lateinit var callback: RepositoryCallback<TO>
    lateinit var mapper: ResponseMapper<FROM, TO>
    lateinit var listener: BaseValueEventListener<FROM, TO>


    abstract fun getRootNode(): String

    fun DatabaseRepository(mapper: ResponseMapper<FROM, TO>) {
        databaseReference = FirebaseDatabase.getInstance().getReference(getRootNode())
        this.mapper = mapper
    }

    fun addListener(firebaseCallback: RepositoryCallback<TO>) {
        this.callback = firebaseCallback
        listener = BaseValueEventListener(
            mapper,
            firebaseCallback
        )
        databaseReference.addValueEventListener(listener)
    }

    fun removeListener() {
        databaseReference.removeEventListener(listener)
    }
}