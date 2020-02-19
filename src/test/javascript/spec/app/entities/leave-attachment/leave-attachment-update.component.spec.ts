/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { LeaveAttachmentUpdateComponent } from 'app/entities/leave-attachment/leave-attachment-update.component';
import { LeaveAttachmentService } from 'app/entities/leave-attachment/leave-attachment.service';
import { LeaveAttachment } from 'app/shared/model/leave-attachment.model';

describe('Component Tests', () => {
    describe('LeaveAttachment Management Update Component', () => {
        let comp: LeaveAttachmentUpdateComponent;
        let fixture: ComponentFixture<LeaveAttachmentUpdateComponent>;
        let service: LeaveAttachmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [LeaveAttachmentUpdateComponent]
            })
                .overrideTemplate(LeaveAttachmentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LeaveAttachmentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LeaveAttachmentService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new LeaveAttachment(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.leaveAttachment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new LeaveAttachment();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.leaveAttachment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
