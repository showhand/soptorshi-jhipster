import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductCategory } from 'app/shared/model/product-category.model';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryDeletePopupComponent, ProductCategoryResolve, ProductCategoryService } from 'app/entities/product-category';
import { ProductCategoryExtendedComponent } from 'app/entities/product-category-extended/product-category-extended.component';
import { ProductCategoryExtendedDetailComponent } from 'app/entities/product-category-extended/product-category-extended-detail.component';
import { ProductCategoryExtendedUpdateComponent } from 'app/entities/product-category-extended/product-category-extended-update.component';
import { ProductCategoryExtendedCreateComponent } from 'app/entities/product-category-extended/product-category-extended-create.component';

@Injectable({ providedIn: 'root' })
export class ProductCategoryExtendedResolve extends ProductCategoryResolve {
    constructor(public service: ProductCategoryService) {
        super(service);
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductCategory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductCategory>) => response.ok),
                map((productCategory: HttpResponse<ProductCategory>) => productCategory.body)
            );
        }
        return of(new ProductCategory());
    }
}

export const productCategoryExtendedRoute: Routes = [
    {
        path: '',
        component: ProductCategoryExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,desc',
            pageTitle: 'ProductCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductCategoryExtendedDetailComponent,
        resolve: {
            productCategory: ProductCategoryExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductCategoryExtendedCreateComponent,
        resolve: {
            productCategory: ProductCategoryExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductCategoryExtendedUpdateComponent,
        resolve: {
            productCategory: ProductCategoryExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCategories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productCategoryExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductCategoryDeletePopupComponent,
        resolve: {
            productCategory: ProductCategoryExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductCategories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
