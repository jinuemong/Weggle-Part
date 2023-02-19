package com.puresoftware.bottomnavigationappbar.Weggler.Unit

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


// 응답으로 null 값이 올 경우 처리
class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter<ResponseBody, Any> {
            if (it.contentLength() == 0L){
                try {
                    delegate.convert(it)
                }catch (e:java.lang.Exception){
                    e.printStackTrace()
                }
            }
        }
    }

}
