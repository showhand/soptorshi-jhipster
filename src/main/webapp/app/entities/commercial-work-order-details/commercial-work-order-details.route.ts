import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialWorkOrderDetails } from 'app/shared/model/commercial-work-order-details.model';
import { CommercialWorkOrderDetailsService } from './commercial-work-order-details.service';
import { CommercialWorkOrderDetailsComponent } from './commercial-work-order-details.component';
import { CommercialWorkOrderDetailsDetailComponent } from './commercial-work-order-details-detail.component';
import { CommercialWorkOrderDetailsUpdateComponent } from './commercial-work-order-details-update.component';
import { CommercialWorkOrderDetailsDeletePopupComponent } from './commercial-work-order-details-delete-dialog.component';
import { ICommercialWorkOrderDetails } from 'app/shared/model/commercial-work-order-details.model';

@Injectable({ providedIn: 'root' })
export class CommercialWorkOrderDetailsResolve implements Resolve<ICommercialWorkOrderDetails> {
    constructor(private service: CommercialWorkOrderDetailsService) {}

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

export const commercialWorkOrderDetailsRoute: Routes = [
    {
        path: '',
        component: CommercialWorkOrderDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialWorkOrderDetailsDetailComponent,
        resolve: {
            commercialWorkOrderDetails: CommercialWorkOrderDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialWorkOrderDetailsUpdateComponent,
        resolve: {
            commercialWorkOrderDetails: CommercialWorkOrderDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialWorkOrderDetailsUpdateComponent,
        resolve: {
            commercialWorkOrderDetails: CommercialWorkOrderDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialWorkOrderDetailsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialWorkOrderDetailsDeletePopupComponent,
        resolve: {
            commercialWorkOrderDetails: CommercialWorkOrderDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrderDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
