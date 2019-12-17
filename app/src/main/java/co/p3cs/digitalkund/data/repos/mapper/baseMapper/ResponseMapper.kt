package co.p3cs.digitalkund.data.repos.mapper.baseMapper

import com.google.firebase.database.DataSnapshot
import java.lang.reflect.ParameterizedType
import java.util.*

abstract class ResponseMapper<Entity, Model> :
    IMapper<Entity, Model> {

    private val entityClass: Class<Entity>
        get() {
            val superclass = javaClass.genericSuperclass as ParameterizedType?
            return superclass!!.actualTypeArguments[0] as Class<Entity>
        }

    fun map(dataSnapshot: DataSnapshot): Model {
        val entity = dataSnapshot.getValue(entityClass)
        return map(entity)
    }

    fun mapList(dataSnapshot: DataSnapshot): List<Model> {
        val list = ArrayList<Model>()
        for (item in dataSnapshot.children) {
            list.add(map(item))
        }
        return list
    }

}
