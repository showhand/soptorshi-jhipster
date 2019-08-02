import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryService } from './item-category.service';
import { ItemCategoryComponent } from './item-category.component';
import { ItemCategoryDetailComponent } from './item-category-detail.component';
import { ItemCategoryUpdateComponent } from './item-category-update.component';
import { ItemCategoryDeletePopupComponent } from './item-category-delete-dialog.component';
import { IItemCategory } from 'app/shared/model/item-category.model';

@Injectable({ providedIn: 'root' })
export class ItemCategoryResolve implements Resolve<IItemCategory> {
    constructor(private service: ItemCategoryService) {}

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

export const itemCategoryRoute: Routes = [
    {
        path: '',
        component: ItemCategoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ItemCategoryDetailComponent,
        resolve: {
            itemCategory: ItemCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ItemCategoryUpdateComponent,
        resolve: {
            itemCategory: ItemCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ItemCategoryUpdateComponent,
        resolve: {
            itemCategory: ItemCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemCategoryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ItemCategoryDeletePopupComponent,
        resolve: {
            itemCategory: ItemCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemCategories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
