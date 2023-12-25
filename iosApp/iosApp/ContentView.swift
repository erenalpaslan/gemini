import UIKit
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    init() {
        HelperKt.doInitKoin()
     }

    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ZStack {
            Color.black
                .edgesIgnoringSafeArea(.all)
            ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
                .edgesIgnoringSafeArea(.top)
        }
    }
}
