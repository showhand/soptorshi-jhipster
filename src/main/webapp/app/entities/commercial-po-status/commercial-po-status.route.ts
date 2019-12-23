import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPoStatus } from 'app/shared/model/commercial-po-status.model';
import { CommercialPoStatusService } from './commercial-po-status.service';
import { CommercialPoStatusComponent } from './commercial-po-status.component';
import { CommercialPoStatusDetailComponent } from './commercial-po-status-detail.component';
import { CommercialPoStatusUpdateComponent } from './commercial-po-status-update.component';
import { CommercialPoStatusDeletePopupComponent } from './commercial-po-status-delete-dialog.component';
import { ICommercialPoStatus } from 'app/shared/model/commercial-po-status.model';

@Injectable({ providedIn: 'root' })
export class CommercialPoStatusResolve implements Resolve<ICommercialPoStatus> {
    constructor(private service: CommercialPoStatusService) {}

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

export const commercialPoStatusRoute: Routes = [
    {
        path: '',
        component: CommercialPoStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPoStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPoStatusDetailComponent,
        resolve: {
            commercialPoStatus: CommercialPoStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPoStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPoStatusUpdateComponent,
        resolve: {
            commercialPoStatus: CommercialPoStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPoStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPoStatusUpdateComponent,
        resolve: {
            commercialPoStatus: CommercialPoStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPoStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPoStatusPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPoStatusDeletePopupComponent,
        resolve: {
            commercialPoStatus: CommercialPoStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPoStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
