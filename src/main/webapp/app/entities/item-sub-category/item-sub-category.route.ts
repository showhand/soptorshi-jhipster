import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { ItemSubCategoryService } from './item-sub-category.service';
import { ItemSubCategoryComponent } from './item-sub-category.component';
import { ItemSubCategoryDetailComponent } from './item-sub-category-detail.component';
import { ItemSubCategoryUpdateComponent } from './item-sub-category-update.component';
import { ItemSubCategoryDeletePopupComponent } from './item-sub-category-delete-dialog.component';
import { IItemSubCategory } from 'app/shared/model/item-sub-category.model';

@Injectable({ providedIn: 'root' })
export class ItemSubCategoryResolve implements Resolve<IItemSubCategory> {
    constructor(private service: ItemSubCategoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IItemSubCategory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ItemSubCategory>) => response.ok),
                map((itemSubCategory: HttpResponse<ItemSubCategory>) => itemSubCategory.body)
            );
        }
        return of(new ItemSubCategory());
    }
}

export const itemSubCategoryRoute: Routes = [
    {
        path: '',
        component: ItemSubCategoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemSubCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ItemSubCategoryDetailComponent,
        resolve: {
            itemSubCategory: ItemSubCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemSubCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ItemSubCategoryUpdateComponent,
        resolve: {
            itemSubCategory: ItemSubCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemSubCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ItemSubCategoryUpdateComponent,
        resolve: {
            itemSubCategory: ItemSubCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemSubCategories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemSubCategoryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ItemSubCategoryDeletePopupComponent,
        resolve: {
            itemSubCategory: ItemSubCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemSubCategories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
