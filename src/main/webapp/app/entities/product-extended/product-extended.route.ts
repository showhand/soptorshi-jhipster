import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Product } from 'app/shared/model/product.model';
import { IProduct } from 'app/shared/model/product.model';
import { ProductDeletePopupComponent, ProductResolve, ProductService } from 'app/entities/product';
import { ProductExtendedComponent } from 'app/entities/product-extended/product-extended.component';
import { ProductExtendedDetailComponent } from 'app/entities/product-extended/product-extended-detail.component';
import { ProductExtendedUpdateComponent } from 'app/entities/product-extended/product-extended-update.component';

@Injectable({ providedIn: 'root' })
export class ProductExtendedResolve extends ProductResolve {
    constructor(public service: ProductService) {
        super(service);
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProduct> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Product>) => response.ok),
                map((product: HttpResponse<Product>) => product.body)
            );
        }
        return of(new Product());
    }
}

export const productExtendedRoute: Routes = [
    {
        path: '',
        component: ProductExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductExtendedDetailComponent,
        resolve: {
            product: ProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductExtendedUpdateComponent,
        resolve: {
            product: ProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductExtendedUpdateComponent,
        resolve: {
            product: ProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductDeletePopupComponent,
        resolve: {
            product: ProductResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Products'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
