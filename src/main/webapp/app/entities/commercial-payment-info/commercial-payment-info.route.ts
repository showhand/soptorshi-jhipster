import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPaymentInfo, ICommercialPaymentInfo } from 'app/shared/model/commercial-payment-info.model';
import { CommercialPaymentInfoService } from './commercial-payment-info.service';
import { CommercialPaymentInfoComponent } from './commercial-payment-info.component';
import { CommercialPaymentInfoDetailComponent } from './commercial-payment-info-detail.component';
import { CommercialPaymentInfoUpdateComponent } from './commercial-payment-info-update.component';
import { CommercialPaymentInfoDeletePopupComponent } from './commercial-payment-info-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class CommercialPaymentInfoResolve implements Resolve<ICommercialPaymentInfo> {
    constructor(private service: CommercialPaymentInfoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialPaymentInfo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialPaymentInfo>) => response.ok),
                map((commercialPaymentInfo: HttpResponse<CommercialPaymentInfo>) => commercialPaymentInfo.body)
            );
        }
        return of(new CommercialPaymentInfo());
    }
}

export const commercialPaymentInfoRoute: Routes = [
    {
        path: '',
        component: CommercialPaymentInfoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPaymentInfoDetailComponent,
        resolve: {
            commercialPaymentInfo: CommercialPaymentInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPaymentInfoUpdateComponent,
        resolve: {
            commercialPaymentInfo: CommercialPaymentInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPaymentInfoUpdateComponent,
        resolve: {
            commercialPaymentInfo: CommercialPaymentInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPaymentInfoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPaymentInfoDeletePopupComponent,
        resolve: {
            commercialPaymentInfo: CommercialPaymentInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
