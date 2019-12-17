package co.p3cs.digitalkund.data.repos.baseRepo

import co.p3cs.digitalkund.data.repos.mapper.baseMapper.ResponseMapper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class BaseValueEventListener<Entity, Model>(
    private val mapper: ResponseMapper<Entity, Model>,
    private val callback: RepositoryCallback<Model>
) :
    ValueEventListener {

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        val data = mapper.mapList(dataSnapshot)
        callback.onSuccess(data)
    }

    override fun onCancelled(databaseError: DatabaseError) {
        callback.onError(databaseError.message)
    }
}