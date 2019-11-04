import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPaymentInfo, ICommercialPaymentInfo } from 'app/shared/model/commercial-payment-info.model';
import { CommercialPaymentInfoExtendedService } from './commercial-payment-info-extended.service';
import { CommercialPaymentInfoExtendedComponent } from './commercial-payment-info-extended.component';
import { CommercialPaymentInfoDetailExtendedComponent } from './commercial-payment-info-detail-extended.component';
import { CommercialPaymentInfoUpdateExtendedComponent } from './commercial-payment-info-update-extended.component';
import { CommercialPaymentInfoDeletePopupExtendedComponent } from './commercial-payment-info-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class CommercialPaymentInfoExtendedResolve implements Resolve<ICommercialPaymentInfo> {
    constructor(private service: CommercialPaymentInfoExtendedService) {}

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

export const commercialPaymentInfoExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialPaymentInfoExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPaymentInfoDetailExtendedComponent,
        resolve: {
            commercialPaymentInfo: CommercialPaymentInfoExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPaymentInfoUpdateExtendedComponent,
        resolve: {
            commercialPaymentInfo: CommercialPaymentInfoExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPaymentInfoUpdateExtendedComponent,
        resolve: {
            commercialPaymentInfo: CommercialPaymentInfoExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPaymentInfoPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPaymentInfoDeletePopupExtendedComponent,
        resolve: {
            commercialPaymentInfo: CommercialPaymentInfoExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
