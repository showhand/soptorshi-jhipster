import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RequisitionMessages } from 'app/shared/model/requisition-messages.model';
import { RequisitionMessagesExtendedService } from './requisition-messages-extended.service';
import { RequisitionMessagesExtendedComponent } from './requisition-messages-extended.component';
import { RequisitionMessagesExtendedDetailComponent } from './requisition-messages-extended-detail.component';
import { RequisitionMessagesExtendedUpdateComponent } from './requisition-messages-extended-update.component';
import { IRequisitionMessages } from 'app/shared/model/requisition-messages.model';
import { RequisitionMessagesDeletePopupComponent } from 'app/entities/requisition-messages';

@Injectable({ providedIn: 'root' })
export class RequisitionMessagesExtendedResolve implements Resolve<IRequisitionMessages> {
    constructor(private service: RequisitionMessagesExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRequisitionMessages> {
        const id = route.params['id'] ? route.params['id'] : null;
        const requisitionId = route.params['requisitionId'] ? route.params['requisitionId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RequisitionMessages>) => response.ok),
                map((requisitionMessages: HttpResponse<RequisitionMessages>) => requisitionMessages.body)
            );
        } else if (requisitionId) {
            const requisitionMessages = new RequisitionMessages();
            requisitionMessages.requisitionId = requisitionId;
            return of(requisitionMessages);
        }
        return of(new RequisitionMessages());
    }
}

export const requisitionMessagesExtendedRoute: Routes = [
    {
        path: '',
        component: RequisitionMessagesExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'RequisitionMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RequisitionMessagesExtendedDetailComponent,
        resolve: {
            requisitionMessages: RequisitionMessagesExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RequisitionMessagesExtendedUpdateComponent,
        resolve: {
            requisitionMessages: RequisitionMessagesExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':requisitionId/new',
        component: RequisitionMessagesExtendedUpdateComponent,
        resolve: {
            requisitionMessages: RequisitionMessagesExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RequisitionMessagesExtendedUpdateComponent,
        resolve: {
            requisitionMessages: RequisitionMessagesExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionMessages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requisitionMessagesExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RequisitionMessagesDeletePopupComponent,
        resolve: {
            requisitionMessages: RequisitionMessagesExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionMessages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
