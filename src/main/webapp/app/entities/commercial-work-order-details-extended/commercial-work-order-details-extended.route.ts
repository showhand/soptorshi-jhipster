import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialWorkOrderDetails, ICommercialWorkOrderDetails } from 'app/shared/model/commercial-work-order-details.model';
import { CommercialWorkOrderDetailsExtendedService } from './commercial-work-order-details-extended.service';
import { CommercialWorkOrderDetailsExtendedComponent } from './commercial-work-order-details-extended.component';
import { CommercialWorkOrderDetailsDetailExtendedComponent } from './commercial-work-order-details-detail-extended.component';
import { CommercialWorkOrderDetailsUpdateExtendedComponent } from './commercial-work-order-details-update-extended.component';
import { CommercialWorkOrderDetailsDeletePopupExtendedComponent } from './commercial-work-order-details-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class CommercialWorkOrderDetailsExtendedResolve implements Resolve<ICommercialWorkOrderDetails> {
    constructor(private service: CommercialWorkOrderDetailsExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialWorkOrderDetails> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialWorkOrderDetails>) => response.ok),
                map((commercialWorkOrderDetails: HttpResponse<CommercialWorkOrderDetails>) => commercialWorkOrderDetails.body)
            );
        }
        return of(new CommercialWorkOrderDetails());
    }
}

export const commercialWorkOrderDetailsExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialWorkOrderDetailsExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialWorkOrderDetailsDetailExtendedComponent,
        resolve: {
            commercialWorkOrderDetails: CommercialWorkOrderDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialWorkOrderDetailsUpdateExtendedComponent,
        resolve: {
            commercialWorkOrderDetails: CommercialWorkOrderDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialWorkOrderDetailsUpdateExtendedComponent,
        resolve: {
            commercialWorkOrderDetails: CommercialWorkOrderDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialWorkOrderDetailsPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialWorkOrderDetailsDeletePopupExtendedComponent,
        resolve: {
            commercialWorkOrderDetails: CommercialWorkOrderDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrderDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
