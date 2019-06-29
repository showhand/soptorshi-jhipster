/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { LeaveAttachmentDetailComponent } from 'app/entities/leave-attachment/leave-attachment-detail.component';
import { LeaveAttachment } from 'app/shared/model/leave-attachment.model';

describe('Component Tests', () => {
    describe('LeaveAttachment Management Detail Component', () => {
        let comp: LeaveAttachmentDetailComponent;
        let fixture: ComponentFixture<LeaveAttachmentDetailComponent>;
        const route = ({ data: of({ leaveAttachment: new LeaveAttachment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [LeaveAttachmentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LeaveAttachmentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LeaveAttachmentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.leaveAttachment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
