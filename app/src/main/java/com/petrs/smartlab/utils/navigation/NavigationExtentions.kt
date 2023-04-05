package com.petrs.smartlab.utils.navigation

import android.content.Intent
import android.util.SparseArray
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.petrs.smartlab.ui.activities.main.MainActivity

fun BottomNavigationView.setupWithNavController(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent,
    activity: MainActivity
) {
    val graphIdToTagMap = SparseArray<String>()

    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )

        val graphId = navHostFragment.navController.graph.id

        graphIdToTagMap.put(graphId, fragmentTag)

        if (this.selectedItemId == graphId) {
            attachNavHostFragment(fragmentManager, navHostFragment, index == 0)
        } else {
            detachNavHostFragment(fragmentManager, navHostFragment)
        }
    }

    var selectedItemTag = graphIdToTagMap[this.selectedItemId]

    fragmentManager.beginTransaction().addToBackStack(selectedItemTag).commit()

    setOnItemSelectedListener { item ->
        if (fragmentManager.isStateSaved) {
            false
        } else {
            val newlySelectedItemTag = graphIdToTagMap[item.itemId]
            if (selectedItemTag != newlySelectedItemTag) {
                val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                        as NavHostFragment

                fragmentManager.beginTransaction()
                    .attach(selectedFragment)
                    .setPrimaryNavigationFragment(selectedFragment)
                    .detach(fragmentManager.findFragmentByTag(selectedItemTag)!!)
                    .setReorderingAllowed(true)
                    .commit()
                fragmentManager.moveToTopOfBackStack(newlySelectedItemTag)
                selectedItemTag = newlySelectedItemTag
                true
            } else {
                false
            }
        }
    }

    setupItemReselected(graphIdToTagMap, fragmentManager)

    setupDeepLinks(navGraphIds, fragmentManager, containerId, intent)

    fragmentManager.addOnBackStackChangedListener {
        if (fragmentManager.backStackEntryCount == 0) {
            activity.finishAffinity()
        } else {
            val currentTag =
                fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).name
            if (selectedItemTag != currentTag) {
                this.selectedItemId =
                    graphIdToTagMap.keyAt(graphIdToTagMap.indexOfValue(currentTag))
            }
        }
    }
}

private fun BottomNavigationView.setupDeepLinks(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent
) {
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )

        if (navHostFragment.navController.handleDeepLink(intent)
            && selectedItemId != navHostFragment.navController.graph.id) {
            this.selectedItemId = navHostFragment.navController.graph.id
        }
    }
}

private fun BottomNavigationView.setupItemReselected(
    graphIdToTagMap: SparseArray<String>,
    fragmentManager: FragmentManager
) {
    setOnItemReselectedListener { item ->
        val newlySelectedItemTag = graphIdToTagMap[item.itemId]

        fragmentManager.moveToTopOfBackStack(newlySelectedItemTag)

        val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                as NavHostFragment
        val navController = selectedFragment.navController

        navController.popBackStack(
            navController.graph.startDestinationId, false
        )
    }
}

private fun FragmentManager.moveToTopOfBackStack(newlySelectedItemTag: String) {
    var flag = false
    val listOfMovedTags: ArrayList<String?> = arrayListOf()

    for (index in 0 until backStackEntryCount) {
        if (getBackStackEntryAt(index).name == newlySelectedItemTag) {
            flag = true
        } else if (flag) {
            listOfMovedTags.add(getBackStackEntryAt(index).name)
        }
    }
    popBackStack(newlySelectedItemTag,
        FragmentManager.POP_BACK_STACK_INCLUSIVE)
    for (movedTag in listOfMovedTags) {
        beginTransaction().addToBackStack(movedTag).commit()
    }

    beginTransaction().addToBackStack(newlySelectedItemTag).commit()
}

private fun detachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment
) {
    fragmentManager.beginTransaction()
        .detach(navHostFragment)
        .commitNow()
}

private fun attachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment,
    isPrimaryNavFragment: Boolean
) {
    fragmentManager.beginTransaction()
        .attach(navHostFragment)
        .apply {
            if (isPrimaryNavFragment) {
                setPrimaryNavigationFragment(navHostFragment)
            }
        }
        .commitNow()
}

private fun obtainNavHostFragment(
    fragmentManager: FragmentManager,
    fragmentTag: String,
    navGraphId: Int,
    containerId: Int
): NavHostFragment {

    val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
    existingFragment?.let { return it }


    val navHostFragment = NavHostFragment.create(navGraphId)
    fragmentManager.beginTransaction()
        .add(containerId, navHostFragment, fragmentTag)
        .commitNow()
    return navHostFragment
}

private fun getFragmentTag(index: Int) = "bottomNavigation#$index"