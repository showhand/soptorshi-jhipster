import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductPrice } from 'app/shared/model/product-price.model';
import { ProductPriceService } from './product-price.service';
import { ProductPriceComponent } from './product-price.component';
import { ProductPriceDetailComponent } from './product-price-detail.component';
import { ProductPriceUpdateComponent } from './product-price-update.component';
import { ProductPriceDeletePopupComponent } from './product-price-delete-dialog.component';
import { IProductPrice } from 'app/shared/model/product-price.model';

@Injectable({ providedIn: 'root' })
export class ProductPriceResolve implements Resolve<IProductPrice> {
    constructor(private service: ProductPriceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductPrice> {
        const id = route.params['id'] ? route.params['id'] : null;
        const productId = route.params['productId'] ? route.params['productId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductPrice>) => response.ok),
                map((productPrice: HttpResponse<ProductPrice>) => productPrice.body)
            );
        } else if (productId) {
            const productPrice = new ProductPrice();
            productPrice.productId = productId;
            return of(productPrice);
        }
        return of(new ProductPrice());
    }
}

export const productPriceRoute: Routes = [
    {
        path: '',
        component: ProductPriceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ProductPrices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':productId/product',
        component: ProductPriceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            productPrice: ProductPriceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ProductPrices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductPriceDetailComponent,
        resolve: {
            productPrice: ProductPriceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductPrices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':productId/new',
        component: ProductPriceUpdateComponent,
        resolve: {
            productPrice: ProductPriceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductPrices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductPriceUpdateComponent,
        resolve: {
            productPrice: ProductPriceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductPrices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductPriceUpdateComponent,
        resolve: {
            productPrice: ProductPriceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductPrices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productPricePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductPriceDeletePopupComponent,
        resolve: {
            productPrice: ProductPriceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductPrices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
