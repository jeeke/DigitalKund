package com.p3cs.digitalkund.data.repos.mapper.baseMapper

interface IMapper<From, To> {
    fun map(from: From?): To
}
