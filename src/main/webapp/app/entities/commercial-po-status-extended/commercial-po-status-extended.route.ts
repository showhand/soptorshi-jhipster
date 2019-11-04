import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPoStatus, ICommercialPoStatus } from 'app/shared/model/commercial-po-status.model';
import { CommercialPoStatusExtendedService } from './commercial-po-status-extended.service';
import { CommercialPoStatusExtendedComponent } from './commercial-po-status-extended.component';
import { CommercialPoStatusDetailExtendedComponent } from './commercial-po-status-detail-extended.component';
import { CommercialPoStatusUpdateExtendedComponent } from './commercial-po-status-update-extended.component';
import { CommercialPoStatusDeletePopupExtendedComponent } from './commercial-po-status-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class CommercialPoStatusExtendedResolve implements Resolve<ICommercialPoStatus> {
    constructor(private service: CommercialPoStatusExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialPoStatus> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialPoStatus>) => response.ok),
                map((commercialPoStatus: HttpResponse<CommercialPoStatus>) => commercialPoStatus.body)
            );
        }
        return of(new CommercialPoStatus());
    }
}

export const commercialPoStatusExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialPoStatusExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPoStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPoStatusDetailExtendedComponent,
        resolve: {
            commercialPoStatus: CommercialPoStatusExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPoStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPoStatusUpdateExtendedComponent,
        resolve: {
            commercialPoStatus: CommercialPoStatusExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPoStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPoStatusUpdateExtendedComponent,
        resolve: {
            commercialPoStatus: CommercialPoStatusExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPoStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPoStatusPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPoStatusDeletePopupExtendedComponent,
        resolve: {
            commercialPoStatus: CommercialPoStatusExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPoStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
