import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ItemCategory } from 'app/shared/model/item-category.model';
import { IItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryServiceExtended } from 'app/entities/item-category-extended/item-category.service.extended';
import { ItemCategoryComponentExtended } from 'app/entities/item-category-extended/item-category.component.extended';
import { ItemCategoryDetailComponentExtended } from 'app/entities/item-category-extended/item-category-detail.component.extended';
import { ItemCategoryUpdateComponentExtended } from 'app/entities/item-category-extended/item-category-update.component.extended';
import { ItemCategoryDeletePopupComponentExtended } from 'app/entities/item-category-extended/item-category-delete-dialog.component.extended';

@Injectable({ providedIn: 'root' })
export class ItemCategoryResolveExtended implements Resolve<IItemCategory> {
    constructor(private service: ItemCategoryServiceExtended) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IItemCategory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ItemCategory>) => response.ok),
                map((itemCategory: HttpResponse<ItemCategory>) => itemCategory.body)
            );
        }
        return of(new ItemCategory());
    }
}

export const itemCategoryRouteExtended: Routes = [
    {
        path: '',
        component: ItemCategoryComponentExtended,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ItemCategoryDetailComponentExtended,
        resolve: {
            itemCategory: ItemCategoryResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ItemCategoryUpdateComponentExtended,
        resolve: {
            itemCategory: ItemCategoryResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ItemCategoryUpdateComponentExtended,
        resolve: {
            itemCategory: ItemCategoryResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemCategoryPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: ItemCategoryDeletePopupComponentExtended,
        resolve: {
            itemCategory: ItemCategoryResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
