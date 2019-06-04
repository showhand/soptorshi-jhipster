/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { AllowanceManagementDeleteDialogComponent } from 'app/entities/allowance-management/allowance-management-delete-dialog.component';
import { AllowanceManagementService } from 'app/entities/allowance-management/allowance-management.service';

describe('Component Tests', () => {
    describe('AllowanceManagement Management Delete Component', () => {
        let comp: AllowanceManagementDeleteDialogComponent;
        let fixture: ComponentFixture<AllowanceManagementDeleteDialogComponent>;
        let service: AllowanceManagementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AllowanceManagementDeleteDialogComponent]
            })
                .overrideTemplate(AllowanceManagementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AllowanceManagementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AllowanceManagementService);
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
