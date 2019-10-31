import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IItemCategory, ItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryExtendedService } from 'app/entities/item-category-extended/item-category-extended.service';
import { ItemCategoryExtendedComponent } from 'app/entities/item-category-extended/item-category-extended.component';
import { ItemCategoryDetailExtendedComponent } from 'app/entities/item-category-extended/item-category-detail-extended.component';
import { ItemCategoryUpdateExtendedComponent } from 'app/entities/item-category-extended/item-category-update-extended.component';
import { ItemCategoryDeletePopupExtendedComponent } from 'app/entities/item-category-extended/item-category-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class ItemCategoryExtendedResolve implements Resolve<IItemCategory> {
    constructor(private service: ItemCategoryExtendedService) {}

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

export const itemCategoryExtendedRoute: Routes = [
    {
        path: '',
        component: ItemCategoryExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ItemCategoryDetailExtendedComponent,
        resolve: {
            itemCategory: ItemCategoryExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ItemCategoryUpdateExtendedComponent,
        resolve: {
            itemCategory: ItemCategoryExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ItemCategoryUpdateExtendedComponent,
        resolve: {
            itemCategory: ItemCategoryExtendedResolve
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
        component: ItemCategoryDeletePopupExtendedComponent,
        resolve: {
            itemCategory: ItemCategoryExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
