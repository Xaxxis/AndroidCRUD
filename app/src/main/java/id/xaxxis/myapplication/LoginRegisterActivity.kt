package id.xaxxis.myapplication

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import id.xaxxis.myapplication.mvp.MainView
import id.xaxxis.myapplication.mvp.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_register.view.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk27.coroutines.onClick

class LoginRegisterActivity : AppCompatActivity(), MainView {

    private var strlevel : String? = null
    var mainPresenter : MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mainPresenter = MainPresenter(this)

        //Btn click register
        btnRegister.onClick {
        registeruser()
        }

        //btn click login form login activity login layout
        btnSignIn.onClick {

        }
    }

    private fun registeruser() {
        val dialogregis = Dialog(this, R.style.MyAlertDialogTheme)
        val tampilanRegis = layoutInflater.inflate(R.layout.layout_register, null)

        dialogregis.setContentView(tampilanRegis)
        if (tampilanRegis.regAdmin.isChecked){
            strlevel = "admin"
        }else{
            strlevel = "mahasiswa"
        }

        tampilanRegis.register.onClick {
            if (TextUtils.isEmpty(tampilanRegis.edtEmail.toString())) {
                tampilanRegis.edtEmail.error = "Email tidak boleh kosong"
            } else if (TextUtils.isEmpty(tampilanRegis.edtPassword.toString())) {
                tampilanRegis.edtPassword.error = "Password tidak boleh kosong"
            } else if (tampilanRegis.edtPassword.toString().length < 6){
                tampilanRegis.edtPassword.error = "Password tidak boleh lebih kecil dari 6"
            }else{
                prosesregister(tampilanRegis, dialogregis)
            }
        }
            dialogregis.show()
    }
        private  fun prosesregister(tampilanregis: View?, dialogregis: Dialog){
            mainPresenter?.register(tampilanregis?.edtEmail?.text.toString(),
                tampilanregis?.edtPassword?.text.toString(),
                strlevel, dialogregis)

    }

    override fun showLoading() {
    //Menampilkan progress bar
        pb.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb.visibility = View.GONE
    }

    override fun showMessage(isipesan: String?) {
        longToast(isipesan.toString())
    }

    override fun hideDialog(d: Dialog) {
        d.dismiss()
    }
}