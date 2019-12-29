import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RequisitionMessages } from 'app/shared/model/requisition-messages.model';
import { RequisitionMessagesService } from './requisition-messages.service';
import { RequisitionMessagesComponent } from './requisition-messages.component';
import { RequisitionMessagesDetailComponent } from './requisition-messages-detail.component';
import { RequisitionMessagesUpdateComponent } from './requisition-messages-update.component';
import { RequisitionMessagesDeletePopupComponent } from './requisition-messages-delete-dialog.component';
import { IRequisitionMessages } from 'app/shared/model/requisition-messages.model';

@Injectable({ providedIn: 'root' })
export class RequisitionMessagesResolve implements Resolve<IRequisitionMessages> {
    constructor(private service: RequisitionMessagesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRequisitionMessages> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RequisitionMessages>) => response.ok),
                map((requisitionMessages: HttpResponse<RequisitionMessages>) => requisitionMessages.body)
            );
        }
        return of(new RequisitionMessages());
    }
}

export const requisitionMessagesRoute: Routes = [
    {
        path: '',
        component: RequisitionMessagesComponent,
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
        component: RequisitionMessagesDetailComponent,
        resolve: {
            requisitionMessages: RequisitionMessagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RequisitionMessagesUpdateComponent,
        resolve: {
            requisitionMessages: RequisitionMessagesResolve
        },
        data: {
            authorities: ['ROLE_USER, ROLE_REQUISITIONER'],
            pageTitle: 'RequisitionMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RequisitionMessagesUpdateComponent,
        resolve: {
            requisitionMessages: RequisitionMessagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionMessages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requisitionMessagesPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RequisitionMessagesDeletePopupComponent,
        resolve: {
            requisitionMessages: RequisitionMessagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionMessages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
