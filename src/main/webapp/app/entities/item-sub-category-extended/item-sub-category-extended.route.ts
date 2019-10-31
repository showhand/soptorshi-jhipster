import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IItemSubCategory, ItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { ItemSubCategoryExtendedService } from 'app/entities/item-sub-category-extended/item-sub-category-extended.service';
import { ItemSubCategoryExtendedComponent } from 'app/entities/item-sub-category-extended/item-sub-category-extended.component';
import { ItemSubCategoryDetailExtendedComponent } from 'app/entities/item-sub-category-extended/item-sub-category-detail-extended.component';
import { ItemSubCategoryUpdateExtendedComponent } from 'app/entities/item-sub-category-extended/item-sub-category-update-extended.component';
import { ItemSubCategoryDeletePopupExtendedComponent } from 'app/entities/item-sub-category-extended/item-sub-category-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class ItemSubCategoryResolveExtended implements Resolve<IItemSubCategory> {
    constructor(private service: ItemSubCategoryExtendedService) {}

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

export const itemSubCategoryExtendedRoute: Routes = [
    {
        path: '',
        component: ItemSubCategoryExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ItemSubCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ItemSubCategoryDetailExtendedComponent,
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
        component: ItemSubCategoryUpdateExtendedComponent,
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
        component: ItemSubCategoryUpdateExtendedComponent,
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
        component: ItemSubCategoryDeletePopupExtendedComponent,
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
