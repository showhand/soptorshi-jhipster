import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILeaveAttachment, LeaveAttachment } from 'app/shared/model/leave-attachment.model';
import { LeaveAttachmentService } from './leave-attachment.service';
import { LeaveAttachmentComponent } from './leave-attachment.component';
import { LeaveAttachmentDetailComponent } from './leave-attachment-detail.component';
import { LeaveAttachmentUpdateComponent } from './leave-attachment-update.component';
import { LeaveAttachmentDeletePopupComponent } from './leave-attachment-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class LeaveAttachmentResolve implements Resolve<ILeaveAttachment> {
    constructor(private service: LeaveAttachmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILeaveAttachment> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LeaveAttachment>) => response.ok),
                map((leaveAttachment: HttpResponse<LeaveAttachment>) => leaveAttachment.body)
            );
        }
        return of(new LeaveAttachment());
    }
}

export const leaveAttachmentRoute: Routes = [
    {
        path: '',
        component: LeaveAttachmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LeaveAttachmentDetailComponent,
        resolve: {
            leaveAttachment: LeaveAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LeaveAttachmentUpdateComponent,
        resolve: {
            leaveAttachment: LeaveAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LeaveAttachmentUpdateComponent,
        resolve: {
            leaveAttachment: LeaveAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const leaveAttachmentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LeaveAttachmentDeletePopupComponent,
        resolve: {
            leaveAttachment: LeaveAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
