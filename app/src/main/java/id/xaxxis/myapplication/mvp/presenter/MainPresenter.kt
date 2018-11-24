package id.xaxxis.myapplication.mvp.presenter

import android.app.Dialog
import id.xaxxis.myapplication.mvp.MainView
import id.xaxxis.myapplication.mvp.model.ResponseRegister
import id.xaxxis.myapplication.network.InitRetrofit
import id.xaxxis.myapplication.network.RestApi
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import retrofit2.Call
import retrofit2.Response

class MainPresenter(private val view: MainView) {

    //register data ke api
    fun register(username: String, password: String, level: String?, dialog: Dialog) {
        view.showLoading()
        val api: RestApi = InitRetrofit.getInstance()

        //panggil endpoint
        api.registeruser(username, password, level).enqueue(
            object : retrofit2.Callback<ResponseRegister> {
                override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                    //hide progress bar
                    view.hideLoading()

                    //Menampilkan detail error
                    view.showMessage(t.localizedMessage)
                }

                override fun onResponse(call: Call<ResponseRegister>, response: Response<ResponseRegister>) {
                    async(UI) {
                        val hasil = bg {
                            response.body()?.result
                        }
                        val pesan = response.body()?.msg
                        view.hideDialog(dialog)
                        if (hasil.equals(1)) {
                            view.showMessage(pesan)

                        } else {
                            view.showMessage(pesan)
                        }
                    }
                }
            }
        )
    }
}