package cao.cuong.supership.supership.ui.staff.info

import android.content.Context
import cao.cuong.supership.supership.data.model.Staff
import cao.cuong.supership.supership.data.source.LocalRepository
import cao.cuong.supership.supership.data.source.StaffRepository
import cao.cuong.supership.supership.extension.observeOnUiThread
import io.reactivex.subjects.BehaviorSubject

class StaffInfoFragmentViewModel(context: Context) {

    internal val updateProgressStatusObservable = BehaviorSubject.create<Boolean>()
    private val localRepository = LocalRepository(context)
    private val staffRepository = StaffRepository()


    internal fun isLogin() = localRepository.isLogin()

    internal fun getStaffInfo() = staffRepository.getInfo(localRepository.getAccessToken())
            .observeOnUiThread()
            .doOnSubscribe {
                updateProgressStatusObservable.onNext(true)
            }
            .doFinally {
                updateProgressStatusObservable.onNext(false)
            }

    internal fun getLocalStaffInfo() = localRepository.getStaffInfo()

    internal fun saveStaffInfo(staff: Staff) = localRepository.saveStaffInfo(staff)

    internal fun logOut() = localRepository.clearAccessToken()
}
