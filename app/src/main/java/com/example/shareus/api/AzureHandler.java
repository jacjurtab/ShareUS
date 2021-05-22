package com.example.shareus.api;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shareus.R;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.IMultipleAccountPublicClientApplication;
import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.SilentAuthenticationCallback;
import com.microsoft.identity.client.exception.MsalException;

import java.util.Date;
import java.util.List;

public class AzureHandler {

    private static AzureHandler handler;
    public final String TAG = "LOGIN";

    private static String[] SCOPES;
    private static IMultipleAccountPublicClientApplication mMultipleAccountApp;

    private IAccount account;

    public AzureHandler() {
        SCOPES = new String[]{"user.read"};
        account = null;
    }

    public interface AccountCallback {
        void onResult(IAccount account);
    }

    public interface Callback {
         void onResult();
    }

    public static AzureHandler getInstance() {
        if(handler == null) {
            handler = new AzureHandler();
        }

        return handler;
    }

    public void init(Context cxt, Callback callback) {
        PublicClientApplication.createMultipleAccountPublicClientApplication(cxt, R.raw.azure_auth_config,
            new IPublicClientApplication.IMultipleAccountApplicationCreatedListener() {
                @Override
                public void onCreated(IMultipleAccountPublicClientApplication application) {
                    mMultipleAccountApp = application;
                    callback.onResult();
                }

                @Override
                public void onError(MsalException exception) {
                    Log.d(TAG, exception.getMessage());
                }
            }
        );
    }

    public void destroy(Callback callback){
        mMultipleAccountApp.removeAccount(getAccount(), new IMultipleAccountPublicClientApplication.RemoveAccountCallback() {
            @Override
            public void onRemoved() {
                mMultipleAccountApp = null;
                handler = null;
                callback.onResult();
            }

            @Override
            public void onError(@NonNull MsalException exception) {
                mMultipleAccountApp = null;
                handler = null;
                callback.onResult();
            }
        });

//        mMultipleAccountApp.removeAccount(account, new IMultipleAccountPublicClientApplication.RemoveAccountCallback() {
//            @Override
//            public void onRemoved() {
//
//            }
//
//            @Override
//            public void onError(@NonNull MsalException exception) {
//                mMultipleAccountApp = null;
//                handler = null;
//                callback.onResult();
//            }
//        });
    }

    //Authorize new user to the app.
    public void authorize(Activity activity, AccountCallback callback) {
        mMultipleAccountApp.acquireToken(activity, SCOPES, new AuthenticationCallback() {
            @Override
            public void onCancel() {
                callback.onResult(null);
                Log.d(TAG, "Authentication cancelled.");
            }

            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                account = authenticationResult.getAccount();
                callback.onResult(account);
            }

            @Override
            public void onError(MsalException exception) {
                callback.onResult(null);
                Log.d(TAG, "Authentication failed: " + exception.toString());
            }
        });
    }

    //Get authorized accounts.
    private void loadAuthorizedAccounts(Callback callback) {
        if (mMultipleAccountApp == null) {
            return;
        }
        Log.d("LOGIN", "No hay cuenta de Azure instanciada. Se llama a loadAccounts()");

        mMultipleAccountApp.getAccounts(new IPublicClientApplication.LoadAccountsCallback() {
            @Override
            public void onTaskCompleted(final List<IAccount> result) {
                Log.d(TAG, "Obtained  " + result.size() + " accounts.");
                if(result.size() > 0)
                    account = result.get(0);

                callback.onResult();
            }

            @Override
            public void onError(MsalException exception) {
                Log.d(TAG, exception.getMessage());
            }
        });
    }

    public IAccount getAccount() {
        return account;
    }

    public void checkAccount(Callback callback) {
        if(account == null) {
            loadAuthorizedAccounts(() -> {
                Log.d("LOGIN", "LoadAccounts() ha terminado");
                callback.onResult();
            });
        }
    }
}
