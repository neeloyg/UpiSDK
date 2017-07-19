# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\adt-bundle-windows-x86_64-20140702\adt-bundle-windows-x86_64-20140702\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose


-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification




#-keep public class com.upi.sdk.core.UpiSDKContext.UPIPay
#-keepclassmembers public class com.upi.sdk.core.UpiSDKContext.UPIPay {*;}


-keep public class com.upi.sdk.core.UpiSDKContext
-keepclassmembers public class com.upi.sdk.core.UpiSDKContext { *;}

#Inner class declaration by $
-keep public class com.upi.sdk.core.UpiSDKContext$UPIPay
-keepclassmembers public class com.upi.sdk.core.UpiSDKContext$UPIPay { *; }

#Interface call
#-keep public interface com.somepackage.SomeClass$someInterface {*;}

-keep public class org.npci.upi.security.services.CLServices
-keepclassmembers public class org.npci.upi.security.services.CLServices {*;}

#Interface call
-keep public interface org.npci.upi.security.services.ServiceConnectionStatusNotifier {*;}
-keepclassmembers public interface org.npci.upi.security.services.ServiceConnectionStatusNotifier {*;}

#Callback interfcae call Login,Mobilenoverificatin,UserSignUp
-keep public interface com.upi.sdk.domain.APICallback {*;}
-keepclassmembers public interface com.upi.sdk.domain.APICallback {*;}

#optional
-keep public enum com.upi.sdk.errors.UPIErrorCode {*;}
-keepclassmembers public enum com.upi.sdk.errors.UPIErrorCode{*;}
-keep public enum com.upi.sdk.errors$** {
    **[] $VALUES;
    public *;
}

-keep public enum com.upi.sdk.errors.SDKErrorCodes {*;}
-keepclassmembers public enum com.upi.sdk.errors.SDKErrorCodes{*;}

-keep public enum com.upi.sdk.utils.CLLanguage {*;}
-keepclassmembers public enum com.upi.sdk.utils.CLLanguage{*;}

#SDK call from BankAccListAdapter



-keep public class org.npci.upi.security.** {
  public protected *;
}


-keep public class com.upi.sdk.domain.InputAddBankAccount
-keepclassmembers public class com.upi.sdk.domain.InputAddBankAccount {*;}

#SDK call from FragmentNpciTransactionHistory
-keep public class com.rssoftware.upi.upipay.services.Npci_TransactionsHistoryItems
-keepclassmembers public class com.rssoftware.upi.upipay.services.Npci_TransactionsHistoryItems {*;}



#SDK call from Npci_ListBankAccountsAdapter
-keep public class com.upi.sdk.domain.BalanceEnquiryRequest
-keepclassmembers public class com.upi.sdk.domain.BalanceEnquiryRequest {*;}

-keep public class com.upi.sdk.domain.BalanceEnquiryResponse
-keepclassmembers public class com.upi.sdk.domain.BalanceEnquiryResponse {*;}

-keep public class com.upi.sdk.domain.InputDeleteBankAccount
-keepclassmembers public class com.upi.sdk.domain.InputDeleteBankAccount {*;}



#SDK call from NpciVPAMapping

-keep public class com.upi.sdk.domain.InputAddVPA
-keepclassmembers public class com.upi.sdk.domain.InputAddVPA {*;}

-keep public class com.upi.sdk.domain.InListVPAQuery
-keepclassmembers public class com.upi.sdk.domain.InListVPAQuery {*;}



-keep public class com.upi.sdk.domain.InputUpdateVPA
-keepclassmembers public class com.upi.sdk.domain.InputUpdateVPA {*;}

#SDK call from Npci_payments_pending_auth_list
-keep public class com.rssoftware.upi.upipay.services.Npci_PendingTxnsItems
-keepclassmembers public class com.rssoftware.upi.upipay.services.Npci_PendingTxnsItems {*;}

#SDK call from npci_UpiAccountListAdapter
-keep public class com.upi.sdk.domain.InputDeleteVPA
-keepclassmembers public class com.upi.sdk.domain.InputDeleteVPA {*;}

-keep public class com.upi.sdk.domain.InputServiceCharge
-keepclassmembers public class com.upi.sdk.domain.InputServiceCharge {*;}

-keep public class com.upi.sdk.domain.ServiceChargeResult
-keepclassmembers public class com.upi.sdk.domain.ServiceChargeResult {*;}




#SDK call from Npci_payments_pending_auth_list
-keep public class com.upi.sdk.domain.OutPendingTxn
-keepclassmembers public class com.upi.sdk.domain.OutPendingTxn {*;}





#SDK call from Npci_authorize_collection
-keep public class com.upi.sdk.domain.InPaymentAuthorization
-keepclassmembers public class com.upi.sdk.domain.InPaymentAuthorization {*;}




-keep public class com.upi.sdk.domain.InListLinkAccountQueryByMobile
-keepclassmembers public class com.upi.sdk.domain.InListLinkAccountQueryByMobile {*;}

-keep public class com.upi.sdk.domain.VaeDetails
-keepclassmembers public class com.upi.sdk.domain.VaeDetails {*;}




#SDK call from upipay\fragments\collect\Npci_requst_for_money.java
-keep public class com.upi.sdk.domain.UpiPayer
-keepclassmembers public class com.upi.sdk.domain.UpiPayer {*;}

-keep public class com.upi.sdk.domain.UserInfo
-keepclassmembers public class com.upi.sdk.domain.UserInfo {*;}

-keep public class com.upi.sdk.domain.UpdatePassword
-keepclassmembers public class com.upi.sdk.domain.UpdatePassword {*;}

-keep public class com.upi.sdk.domain.UpiPayee
-keepclassmembers public class com.upi.sdk.domain.UpiPayee {*;}

-keep public class com.upi.sdk.domain.InPayRequest
-keepclassmembers public class com.upi.sdk.domain.InPayRequest {*;}


#SDK call from Npci_BeneficiaryAdapter
-keep public class com.upi.sdk.domain.InputDeleteBeneficiary
-keepclassmembers public class com.upi.sdk.domain.InputDeleteBeneficiary {*;}

#SDK call from Npci_BeneficiaryList
-keep public class com.upi.sdk.domain.InputBeneficiary
-keepclassmembers public class com.upi.sdk.domain.InputBeneficiary {*;}

-keep public class com.upi.sdk.domain.InputUpdateBeneficiary
-keepclassmembers public class com.upi.sdk.domain.InputUpdateBeneficiary {*;}

-keep public class com.upi.sdk.domain.OutPayRequest
-keepclassmembers public class com.upi.sdk.domain.OutPayRequest {*;}

-keep public class com.upi.sdk.domain.BankAccountBean
-keepclassmembers public class com.upi.sdk.domain.BankAccountBean {*;}

-keep public class com.upi.sdk.domain.BeneficiaryType
-keepclassmembers public class com.upi.sdk.domain.BeneficiaryType {*;}




#UPISDKException call
-keep public class com.upi.sdk.errors.UPISDKException
-keepclassmembers public class com.upi.sdk.errors.UPISDKException {*;}

#npci_UserSignUp to SDK class call
-keep public class com.upi.sdk.domain.InputAddDefaultVPA
-keepclassmembers public class com.upi.sdk.domain.InputAddDefaultVPA {*;}

-keep public class com.upi.sdk.domain.InputcheckVPAAvailability
-keepclassmembers public class com.upi.sdk.domain.InputcheckVPAAvailability {*;}


-keep public class com.upi.sdk.domain.ChangePassword
-keepclassmembers public class com.upi.sdk.domain.ChangePassword {*;}

-keep public class com.upi.sdk.domain.InputActivateVPA
-keepclassmembers public class com.upi.sdk.domain.InputActivateVPA {*;}

-keep public class com.upi.sdk.domain.InputNotification
-keepclassmembers public class com.upi.sdk.domain.InputNotification {*;}


-keep public class java.util.List.ServiceCallback<?>
-keepclassmembers public class java.util.List.ServiceCallback<?> {*;}

#-keep public class com.upi.sdk.services.ServiceIdentifier<?>
#-keepclassmembers public class com.upi.sdk.services.ServiceIdentifier<?> {*;}

-keep public class com.upi.sdk.services.ServiceExecutor
-keepclassmembers public class com.upi.sdk.services.ServiceExecutor {*;}

-keep public class com.upi.sdk.domain.OutPendingTxn
-keepclassmembers public class com.upi.sdk.domain.OutPendingTxn {*;}

-keep public class com.upi.sdk.domain.InputSchedule
-keepclassmembers public class com.upi.sdk.domain.InputSchedule {*;}

-keep public class com.upi.sdk.domain.Schedule
-keepclassmembers public class com.upi.sdk.domain.Schedule {*;}

-keep public class com.upi.sdk.domain.InputDeleteSchedule
-keepclassmembers public class com.upi.sdk.domain.InputDeleteSchedule {*;}

-keep public class com.upi.sdk.domain.InputChannelId
-keepclassmembers public class com.upi.sdk.domain.InputChannelId {*;}

-keep public class com.upi.sdk.domain.InTxnHistoryQuery
-keepclassmembers public class com.upi.sdk.domain.InTxnHistoryQuery {*;}

-keep public class com.upi.sdk.domain.InputAddUpdateUserLimit
-keepclassmembers public class com.upi.sdk.domain.InputAddUpdateUserLimit {*;}




#-keep class com.upi.sdk.services.ServiceIdentifier.** {*;}


#-keep public abstract class java.util.List.ServiceCallback {public *;protected *; private *;}
#
-keep public abstract class com.upi.sdk.services.ServiceIdentifier {public *;protected *; private *;}
-keepclassmembers public abstract class com.upi.sdk.services.ServiceIdentifier {public *;protected *; private *;}


#MOBILE DOMAIN SECTION PACKAGE

-keep public class com.rssoftware.upiint.schema.** {
  public protected *;
}

##SDK call from Npci_authorize_collection
#-keep public class com.rssoftware.upiint.schema.RaiseDispute
#-keepclassmembers public class com.rssoftware.upiint.schema.RaiseDispute {*;}

#MOBILE DOMAIN SECTION

#-keep public class com.rssoftware.upiint.schema.DeviceDetails
#-keepclassmembers public class com.rssoftware.upiint.schema.DeviceDetails {*;}
#
#-keep public class com.rssoftware.upiint.schema.Device
#-keepclassmembers public class com.rssoftware.upiint.schema.Device {*;}
#
#-keep public class com.rssoftware.upiint.schema.DeviceFingerprint
#-keepclassmembers public class com.rssoftware.upiint.schema.DeviceFingerprint {*;}

#-keep public class com.rssoftware.upiint.schema.User
#-keepclassmembers public class com.rssoftware.upiint.schema.User {*;}

#-keep public class com.rssoftware.upiint.schema.Identity
#-keepclassmembers public class com.rssoftware.upiint.schema.Identity {*;}
#
#
#-keep public class com.rssoftware.upiint.schema.ServiceResponse
#-keepclassmembers public class com.rssoftware.upiint.schema.ServiceResponse {*;}
#
#-keep public class com.rssoftware.upiint.schema.UserView
#-keepclassmembers public class com.rssoftware.upiint.schema.UserView {*;}
#
#-keep public class com.rssoftware.upiint.schema.BeneficiaryDetails
#-keepclassmembers public class com.rssoftware.upiint.schema.BeneficiaryDetails {*;}
#
##SDK call from fragments\bank_accounts\npci_registerMobileBanking.java
#-keep public class com.rssoftware.upiint.schema.ReqRegMobile
#-keepclassmembers public class com.rssoftware.upiint.schema.ReqRegMobile {*;}
#
##SDK call fromfragments\bank_accounts\npciAddByAccountFetch.java
#-keep public class com.rssoftware.upiint.schema.PspBankList
#-keepclassmembers public class com.rssoftware.upiint.schema.PspBankList {*;}
#
##SDK call from npci_FragmentUPIAccountList
#-keep public class com.rssoftware.upiint.schema.Vpa
#-keepclassmembers public class com.rssoftware.upiint.schema.Vpa {*;}
#
##SDK call from upipay\fragments\authorization\BankAccountSelection.java
#-keep public class com.rssoftware.upiint.schema.InputChangeMPIN
#-keepclassmembers public class com.rssoftware.upiint.schema.InputChangeMPIN {*;}
#-keep public class com.rssoftware.upiint.schema.CredAllowed
#-keepclassmembers public class com.rssoftware.upiint.schema.CredAllowed {*;}
#-keep public class com.rssoftware.upiint.schema.ForgetMpin
#-keepclassmembers public class com.rssoftware.upiint.schema.ForgetMpin {*;}
#
## SDK call from BankAccountSelection enum
#-keep public enum com.rssoftware.upiint.schema.CredentialType
#-keepclassmembers public enum com.rssoftware.upiint.schema.CredentialType {*;}
#-keep public enum com.rssoftware.upiint.schema.CredentialSubtype {*;}
#
#
##SDK call from NpciBankAccountSelection
#-keep public interface com.rssoftware.upiint.schema.IStateListener {*;}
#-keepclassmembers public interface com.rssoftware.upiint.schema.IStateListener {*;}
#
#-keep public enum com.rssoftware.upiint.schema.State {*;}
#-keepclassmembers public interface com.rssoftware.upiint.schema.State {*;}
#
#-keep public class com.rssoftware.upiint.schema.VpaPayee
#-keepclassmembers public class com.rssoftware.upiint.schema.VpaPayee {*;}
#
#-keep public class com.rssoftware.upiint.schema.TransactionHistory
#-keepclassmembers public class com.rssoftware.upiint.schema.TransactionHistory {*;}
#
#-keep public class com.rssoftware.upiint.schema.LinkAccountDetails
#-keepclassmembers public class com.rssoftware.upiint.schema.LinkAccountDetails {*;}


#........MOBILE DOMAIN SECTION



#not used
-keep public class com.rssoftware.upiint.schema.ServiceRequest
-keepclassmembers public class com.rssoftware.upiint.schema.ServiceRequest {*;}

-keep public interface com.upi.sdk.services.RegisterAppServices {*;}
-keepclassmembers public interface com.upi.sdk.services.RegisterAppServices {*;}

-keep public class com.upi.sdk.utils.Prefs
-keepclassmembers public class com.upi.sdk.utils.Prefs {*;}

-keep public class com.upi.sdk.utils.AbsPrefs
-keepclassmembers public class com.upi.sdk.utils.AbsPrefs {*;}

-keep public enum com.rssoftware.upiint.schema.ChannelType {*;}


-keep public class com.upi.sdk.CryptLib
-keepclassmembers public class com.upi.sdk.CryptLib {*;}

-keep public class com.upi.sdk.services.HmacGenerator
-keepclassmembers public class com.upi.sdk.services.HmacGenerator {*;}

#common library classes

#-keep public class org.npci.upi.security.pinactivitycomponent** {
#  public protected *;
#}

#-keep public class com.rssoftware.upiint.schema.ConfigElement
#-keepclassmembers public class com.rssoftware.upiint.schema.ConfigElement {*;}
#
#
#-keep public class org.npci.upi.security.pinactivitycomponent.AppProperties
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.AppProperties {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.BuildConfig
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.BuildConfig {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.CLConstants
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.CLConstants {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.CLContext
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.CLContext {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.CLException
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.CLException {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.CLRemoteServiceImpl
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.CLRemoteServiceImpl {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.CLServiceProvider
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.CLServiceProvider {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.Challenge
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.Challenge {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.CryptLib
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.CryptLib {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.DatabaseHandler
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.DatabaseHandler {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.Encryptor
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.Encryptor {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.GetChallengeService
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.GetChallengeService {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.GetCredential
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.GetCredential {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.GetCredentialFragment
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.GetCredentialFragment {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.InputAnalyzer
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.InputAnalyzer {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.JsonUtils
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.JsonUtils {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.LayoutControlDecorator
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.LayoutControlDecorator {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.LayoutPayInfoDecorator
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.LayoutPayInfoDecorator {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.Log
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.Log {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.RegisterAppService
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.RegisterAppService {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.SaltBuilder
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.SaltBuilder {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.SecretItem
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.SecretItem {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.TextValidator
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.TextValidator {*;}
#
#-keep public class org.npci.upi.security.pinactivitycomponent.UIDecorator
#-keepclassmembers public class org.npci.upi.security.pinactivitycomponent.UIDecorator {*;}
#
#-keep public enum org.npci.upi.security.pinactivitycomponent.PayInfoType {*;}
#-keepclassmembers public enum org.npci.upi.security.pinactivitycomponent.PayInfoType{*;}
#-keep public enum org.npci.upi.security.pinactivitycomponent.ValidationType {*;}
#-keepclassmembers public enum org.npci.upi.security.pinactivitycomponent.ValidationType{*;}
#-keep public enum org.npci.upi.security.pinactivitycomponent$** {
#    **[] $VALUES;
#    public *;
#}
#
#-keep public interface org.npci.upi.security.services.CLRemoteService {*;}
#-keepclassmembers public interface org.npci.upi.security.services.CLRemoteService {*;}
#
#-keep public interface org.npci.upi.security.services.CLResultReceiver {*;}
#-keepclassmembers public interface org.npci.upi.security.services.CLResultReceiver {*;}
#




#end common library classes


#Npci_Login to SDK class call
-keep public class com.rssoftware.upiint.schema.ConfigElement
-keepclassmembers public class com.rssoftware.upiint.schema.ConfigElement {*;}

-keepclassmembers class android.support.v4.app.** { *; }
-keepclassmembers interface android.support.v4.app.** { *; }



#-keep public final class com.upi.sdk.UPIPay
#-keepclassmembers public final class com.upi.sdk.UPIPay {*;}

-keep class com.upi.sdk.**
-keep class org.npci.**
-keep class in.org.npci.**
-keep class com.rssoftware.upiint.schema.**
-keep class org.npci.upi.security.**
-keep class retrofit2.converter.gson.**
-keep class com.google.gson.**
-keep class okhttp3.**
-keep class okio.**
-keep class retrofit2.**
-keep interface retrofit2.**
-keep class com.google.** { *; }
#-keep public class com.rssoftware.upiint.schema.*
#-keep class com.upi.sdk.UPIPay.**

## Keep the pojos used by GSON or Jackson
#-keep class com.futurice.project.models.pojo.** { *; }
#
## Keep GSON stuff
#-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.** { *; }
#
## Keep Jackson stuff
#-keep class org.codehaus.** { *; }
#-keep class com.fasterxml.jackson.annotation.** { *; }
#
## Keep these for GSON and Jackson
#-keepattributes Signature
#-keepattributes *Annotation*
#-keepattributes EnclosingMethod
#
## Keep Retrofit
#-keep class retrofit.** { *; }
#-keepclasseswithmembers class * {
#    @retrofit.** *;
#}
#-keepclassmembers class * {
#    @retrofit.** *;
#}

-keepclassmembers enum * { public static **[] values(); public static ** valueOf(java.lang.String); }
-keep class * implements android.os.Parcelable {
 public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
 public static <fields>;
}

#keep static fields of enum
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}

-keepclassmembers,allowoptimization enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#-keepclassmembers class * extends java.lang.Enum {
#    <fields>;
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}


-keepattributes Signature, InnerClasses, Exceptions
-ignorewarnings
-keepattributes *Annotation*

