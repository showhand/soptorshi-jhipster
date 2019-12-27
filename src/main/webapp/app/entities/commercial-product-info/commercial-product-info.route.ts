import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialProductInfo, ICommercialProductInfo } from 'app/shared/model/commercial-product-info.model';
import { CommercialProductInfoService } from './commercial-product-info.service';
import { CommercialProductInfoComponent } from './commercial-product-info.component';
import { CommercialProductInfoDetailComponent } from './commercial-product-info-detail.component';
import { CommercialProductInfoUpdateComponent } from './commercial-product-info-update.component';
import { CommercialProductInfoDeletePopupComponent } from './commercial-product-info-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class CommercialProductInfoResolve implements Resolve<ICommercialProductInfo> {
    constructor(private service: CommercialProductInfoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialProductInfo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialProductInfo>) => response.ok),
                map((commercialProductInfo: HttpResponse<CommercialProductInfo>) => commercialProductInfo.body)
            );
        }
        return of(new CommercialProductInfo());
    }
}

export const commercialProductInfoRoute: Routes = [
    {
        path: '',
        component: CommercialProductInfoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProductInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialProductInfoDetailComponent,
        resolve: {
            commercialProductInfo: CommercialProductInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProductInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialProductInfoUpdateComponent,
        resolve: {
            commercialProductInfo: CommercialProductInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProductInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialProductInfoUpdateComponent,
        resolve: {
            commercialProductInfo: CommercialProductInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProductInfos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialProductInfoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialProductInfoDeletePopupComponent,
        resolve: {
            commercialProductInfo: CommercialProductInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProductInfos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
