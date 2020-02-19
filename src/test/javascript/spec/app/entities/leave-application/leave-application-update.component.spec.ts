/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { LeaveApplicationUpdateComponent } from 'app/entities/leave-application/leave-application-update.component';
import { LeaveApplicationService } from 'app/entities/leave-application/leave-application.service';
import { LeaveApplication } from 'app/shared/model/leave-application.model';

describe('Component Tests', () => {
    describe('LeaveApplication Management Update Component', () => {
        let comp: LeaveApplicationUpdateComponent;
        let fixture: ComponentFixture<LeaveApplicationUpdateComponent>;
        let service: LeaveApplicationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [LeaveApplicationUpdateComponent]
            })
                .overrideTemplate(LeaveApplicationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LeaveApplicationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LeaveApplicationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new LeaveApplication(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.leaveApplication = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new LeaveApplication();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.leaveApplication = entity;
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
