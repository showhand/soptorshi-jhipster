import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { IItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { ItemSubCategoryServiceExtended } from 'app/entities/item-sub-category-extended/item-sub-category.service.extended';
import { ItemSubCategoryComponentExtended } from 'app/entities/item-sub-category-extended/item-sub-category.component.extended';
import { ItemSubCategoryDetailComponentExtended } from 'app/entities/item-sub-category-extended/item-sub-category-detail.component.extended';
import { ItemSubCategoryUpdateComponentExtended } from 'app/entities/item-sub-category-extended/item-sub-category-update.component.extended';
import { ItemSubCategoryDeletePopupComponentExtended } from 'app/entities/item-sub-category-extended/item-sub-category-delete-dialog.component.extended';

@Injectable({ providedIn: 'root' })
export class ItemSubCategoryResolveExtended implements Resolve<IItemSubCategory> {
    constructor(private service: ItemSubCategoryServiceExtended) {}

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

export const itemSubCategoryRouteExtended: Routes = [
    {
        path: '',
        component: ItemSubCategoryComponentExtended,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemSubCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ItemSubCategoryDetailComponentExtended,
        resolve: {
            itemSubCategory: ItemSubCategoryResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemSubCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ItemSubCategoryUpdateComponentExtended,
        resolve: {
            itemSubCategory: ItemSubCategoryResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemSubCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ItemSubCategoryUpdateComponentExtended,
        resolve: {
            itemSubCategory: ItemSubCategoryResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemSubCategories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemSubCategoryPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: ItemSubCategoryDeletePopupComponentExtended,
        resolve: {
            itemSubCategory: ItemSubCategoryResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemSubCategories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
