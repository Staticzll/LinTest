package com.lin.test.fragmentfactory;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

/**
 * created by zll on 2024/2/1 9:46
 */
public class MyFragmentFactory2 extends FragmentFactory {
    private TestRepository repository;

    public MyFragmentFactory2(TestRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
        Class<? extends Fragment> aClass = loadFragmentClass(classLoader, className);
        if(aClass== TestFragment.class){
            return new TestFragment(repository);
        }else{
            return super.instantiate(classLoader, className);
        }
    }
}
