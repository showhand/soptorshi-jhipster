/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { DepartmentHeadDeleteDialogComponent } from 'app/entities/department-head/department-head-delete-dialog.component';
import { DepartmentHeadService } from 'app/entities/department-head/department-head.service';

describe('Component Tests', () => {
    describe('DepartmentHead Management Delete Component', () => {
        let comp: DepartmentHeadDeleteDialogComponent;
        let fixture: ComponentFixture<DepartmentHeadDeleteDialogComponent>;
        let service: DepartmentHeadService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DepartmentHeadDeleteDialogComponent]
            })
                .overrideTemplate(DepartmentHeadDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepartmentHeadDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepartmentHeadService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
