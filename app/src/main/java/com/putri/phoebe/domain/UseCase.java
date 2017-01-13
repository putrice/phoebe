package com.putri.phoebe.domain;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by putri on 1/12/17.
 */

public abstract class UseCase {

    private CompositeSubscription compositeSubscription;

    protected UseCase() {
        initSubscription();
    }

    private void initSubscription() {
        compositeSubscription = new CompositeSubscription();
    }

    protected Subscription addSubscription(Subscription subscription) {
        if(compositeSubscription != null && compositeSubscription.isUnsubscribed()) {
            initSubscription();
        }

        if(subscription != null && subscription.isUnsubscribed()) {
            assert compositeSubscription != null;
            compositeSubscription.remove(subscription);
        }

        assert subscription != null;
        assert compositeSubscription != null;
        compositeSubscription.add(subscription);
        return compositeSubscription;
    }

    public void clearAllSubscription() {
        if(compositeSubscription != null) {
            compositeSubscription.clear();
        }
    }

    protected abstract Observable buildUseCaseObservable();

    public Subscription execute(Subscriber subscriber) {
        return buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
